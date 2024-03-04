"use client";

import React from "react";

import { useAuth } from "@/providers/AuthProvider";

const Login = () => {
  const auth = useAuth();
  return (
    <div>
      <button
        onClick={() => auth.authenticate("terenceburns2@gmail.com", "hello123")}
      >
        ***REMOVED***
      </button>
    </div>
  );
};

export default Login;
