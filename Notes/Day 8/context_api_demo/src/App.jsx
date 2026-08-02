import UserProvider from "./UserContext";
import Home from "./Home";

function App() {
  return (
    <UserProvider>
      <div className="container">
        <h1>Context API Demo</h1>
        <Home />
      </div>
    </UserProvider>
  );
}

export default App;