import Home from "./Home";
import withWelcome from "./withWelcome";

const HomeWithWelcome = withWelcome(Home);

function App() {
  return (
    <div>
      <HomeWithWelcome />
      {/* <Home></Home> */}
    </div>
  );
}

export default App;