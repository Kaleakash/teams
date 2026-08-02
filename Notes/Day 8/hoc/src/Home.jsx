function Home({ user }) {
  return (
    <div className="container">
      <h1>Home Page</h1>

      <h2>Welcome {user}</h2>

      <p>This page is rendered using a Higher-Order Component (HOC).</p>
    </div>
  );
}

export default Home;