import React from "react";

// HOC 
const withWelcome = (WrappedComponent) => {
  return function EnhancedComponent(props) {
    const user = "Raj";
    // we can do any other coding. 
    return <WrappedComponent {...props} user={user} />;
  };
};

export default withWelcome;