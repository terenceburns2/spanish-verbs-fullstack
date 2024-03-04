import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import AuthProvider from "@/providers/AuthProvider";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Spanish verbs",
  description: "Verb conjugations",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="m-0 p-0 w-screen h-screen">
      <body className={`${inter.className} w-screen h-screen`}>
        <AuthProvider>{children}</AuthProvider>
      </body>
    </html>
  );
}
