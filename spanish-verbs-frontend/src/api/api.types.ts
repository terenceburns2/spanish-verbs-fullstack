export interface ApiResponse<T = {}> {
    ok: boolean;
    message?: string;
    response?: T;
    status?: number;
}