import useCounter from "./useCounter";

function Counter() {
  const { count, increment, decrement, reset } = useCounter(100);

  return (
    <div className="counter">
      <h2>Count : {count}</h2>

      <div className="buttons">
        <button onClick={increment}>Increment</button>
        <button onClick={decrement}>Decrement</button>
        <button onClick={reset}>Reset</button>
      </div>
    </div>
  );
}

export default Counter;