const BASE_URL = process.env.NEXT_PUBLIC_BASE_URL;

class Request {

    private static async request(url: string, requestConfig: RequestInit) {
        try {
            const response = await fetch(`${BASE_URL}${url}`, { ...requestConfig, headers: { ...requestConfig.headers, ...this.getAuthHeader }});
            return response;
        } catch (e) {
            console.error(e);
            throw new Error("A network error occurred", { cause: e });
        }   
    }

    private static getAuthHeader() {
        if (this.getAccessToken()) {
            return { Authorization: `Bearer ${this.getAccessToken()}` };
          }
          return undefined;
    }

    private static getAccessToken() {
        return sessionStorage["accessToken"];
      }
 
    static async get(url: string, requestConfig: RequestInit) {
        const response = await this.request(url, { ...requestConfig, method: "GET" });
        return response;
    }

    static async post(url: string, requestConfig: RequestInit) {
        const response = await this.request(url, { ...requestConfig, method: "POST" });
        return response;
    }

    static async put(url: string, requestConfig: RequestInit) {
        const response = await this.request(url, { ...requestConfig, method: "PUT" });
        return response;
    }

    static async delete(url: string, requestConfig: RequestInit) {
        const response = await this.request(url, { ...requestConfig, method: "DELETE" });
        return response;
    }

}

export default Request;