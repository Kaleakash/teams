import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import "@testing-library/jest-dom";
import Counter from "../Counter";

describe("Counter Component Test Cases", () => {

  test("should render counter component", () => {
    render(<Counter />);

    expect(screen.getByText("Increment")).toBeInTheDocument();
    expect(screen.getByText("Decrement")).toBeInTheDocument();
    expect(screen.getByText("Reset")).toBeInTheDocument();
    expect(screen.getByTestId("count")).toHaveTextContent("Count : 0");
  });

  test("should increment the count", async () => {
    const user = userEvent.setup();

    render(<Counter />);

    await user.click(screen.getByText("Increment"));

    expect(screen.getByTestId("count")).toHaveTextContent("Count : 1");
  });

  test("should decrement the count", async () => {
    const user = userEvent.setup();

    render(<Counter />);

    await user.click(screen.getByText("Decrement"));

    expect(screen.getByTestId("count")).toHaveTextContent("Count : -1");
  });

  test("should reset the count", async () => {
    const user = userEvent.setup();

    render(<Counter />);

    await user.click(screen.getByText("Increment"));
    await user.click(screen.getByText("Increment"));

    expect(screen.getByTestId("count")).toHaveTextContent("Count : 2");

    await user.click(screen.getByText("Reset"));

    expect(screen.getByTestId("count")).toHaveTextContent("Count : 0");
  });

});