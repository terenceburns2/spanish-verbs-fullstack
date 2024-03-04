"use client";

import React, { createContext, ReactNode, useContext } from "react";

import { useRouter } from "next/navigation";
import Auth from "@/api/Auth";

interface AuthContextState {
  isAuthTokenPresentAndValid: () => Promise<boolean>;
  authenticate: (email: string, password: string) => void;
}

const AuthContext = createContext<AuthContextState | undefined>(undefined);

const AuthProvider = ({ children }: { children: ReactNode }) => {
  const router = useRouter();

  const isAuthTokenPresentAndValid = async () => {
    const accessToken = sessionStorage["accessToken"];
    if (!accessToken) {
      return false;
    }
    const verifyResponse = await Auth.verifyAuth(accessToken);

    return verifyResponse.response as boolean;
  };

  const authenticate = async (email: string, password: string) => {
    const response = await Auth.login(email, password);

    if (response.ok) {
      sessionStorage["accessToken"] = response.response;
      router.push("/home");
    }
  };

  return (
    <AuthContext.Provider value={{ isAuthTokenPresentAndValid, authenticate }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const auth = useContext(AuthContext);

  if (!auth) {
    throw new Error("This hook needs to be used within a AuthContextProvider");
  }

  return auth;
};

export default AuthProvider;
