import Tabs from "./Tabs";

function App() {
  return (
    <div className="container">
      <h1>Compound Components Demo</h1>

      <Tabs>
        <Tabs.Tab label="Home">
          <h2>Home</h2>
          <p>Welcome to the Home Page.</p>
        </Tabs.Tab>

        <Tabs.Tab label="Profile">
          <h2>Profile</h2>
          <p>This is your Profile Page.</p>
        </Tabs.Tab>

        <Tabs.Tab label="Settings">
          <h2>Settings</h2>
          <p>Manage your application settings here.</p>
        </Tabs.Tab>
      </Tabs>
    </div>
  );
}

export default App;