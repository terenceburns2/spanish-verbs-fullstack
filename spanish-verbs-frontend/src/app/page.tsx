"use client";

import { useEffect } from "react";

import { useAuth } from "@/providers/AuthProvider";
import { useRouter } from "next/navigation";

const RootPage = () => {
  const auth = useAuth();
  const router = useRouter();

  useEffect(() => {
    const redirectPageGivenAuthStatus = async () => {
      const authStatus = true; /*await auth.isAuthTokenPresentAndValid();*/
      if (authStatus) {
        router.push("/home");
      } else {
        router.push("/login");
      }
    };
    redirectPageGivenAuthStatus();
  }, []);
};

export default RootPage;
