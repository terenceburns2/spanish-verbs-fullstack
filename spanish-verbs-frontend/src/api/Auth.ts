import Request from "./Request";
import { ApiResponse } from "./api.types";

import { getErrorMessage } from "@/util/getErrorMessage";

class Auth {
  static async login(email: string, password: string): Promise<ApiResponse<string>> {
    try {
      const response: Response = await Request.post("/signin", {
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });
      const text = await response.text();
      return { ok: response.ok, status: response.status, message: text, response: text };
    } catch (e) {
      return { ok: false, message: getErrorMessage(e) };
    }
  }

  static async register(email: string, password: string) {
    try {
      const response: Response = await Request.post("/register", {
        headers: {
          "Content-type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });
      const message = await response.json();
      return { ok: response.ok, message: message, status: response.status };
    } catch (e) {
      return { ok: false, message: getErrorMessage(e) };
    }
  }

  static async verifyAuth(token: string): Promise<ApiResponse<boolean>> {
    try {
      const response: Response = await Request.get(
        `/verify-token?token=${token}`,
        {
          headers: {
            "Content-type": "application/x-www-form-urlencoded",
          },
        }
      );
      const message = await response.json();
      return { ok: response.ok, response: message, status: response.status };
    } catch (e) {
      return { ok: false, message: getErrorMessage(e) };
    }
  }
}

export default Auth;
