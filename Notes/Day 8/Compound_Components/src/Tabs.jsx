import { useState } from "react";

function Tabs({ children }) {
  const [activeTab, setActiveTab] = useState(0);

  return (
    <div>
      <div className="tab-buttons">
        {children.map((child, index) => (
          <button
            key={index}
            className={activeTab === index ? "active" : ""}
            onClick={() => setActiveTab(index)}
          >
            {child.props.label}
          </button>
        ))}
      </div>

      <div className="tab-content">
        {children[activeTab]}
      </div>
    </div>
  );
}

function Tab({ children }) {
  return <>{children}</>;
}

Tabs.Tab = Tab;

export default Tabs;