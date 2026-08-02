import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

const schema = yup.object({
  fullName: yup
    .string()
    .required("Full Name is required"),

  email: yup
    .string()
    .email("Invalid Email")
    .required("Email is required"),

  password: yup
    .string()
    .min(6, "Password must be at least 6 characters")
    .required("Password is required"),

  age: yup
    .number()
    .typeError("Age must be a number")
    .positive()
    .integer()
    .required("Age is required"),
});

function RegistrationForm() {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm({
    resolver: yupResolver(schema),
  });

  const onSubmit = (data) => {
    console.log("Form Submitted");
    console.log(data);

    alert("Registration Successful");

    reset();
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)}>

      <div className="form-group">
        <label>Full Name</label>

        <input
          type="text"
          placeholder="Enter Full Name"
          {...register("fullName")}
        />

        <span>{errors.fullName?.message}</span>
      </div>

      <div className="form-group">
        <label>Email</label>

        <input
          type="email"
          placeholder="Enter Email"
          {...register("email")}
        />

        <span>{errors.email?.message}</span>
      </div>

      <div className="form-group">
        <label>Password</label>

        <input
          type="password"
          placeholder="Enter Password"
          {...register("password")}
        />

        <span>{errors.password?.message}</span>
      </div>

      <div className="form-group">
        <label>Age</label>

        <input
          type="number"
          placeholder="Enter Age"
          {...register("age")}
        />

        <span>{errors.age?.message}</span>
      </div>

      <button type="submit">
        Register
      </button>

    </form>
  );
}

export default RegistrationForm;