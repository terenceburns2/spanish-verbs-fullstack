import React from "react";

const Navbar = () => {
  return (
    <nav className="flex justify-between items-center h-12 bg-indigo-500 p-4 rounded-b-lg">
      <h4>Spanish verb conjugations</h4>
      <button>Logout</button>
    </nav>
  );
};

export default Navbar;
