export class ApiResponse{
    public status:number;
    public message:String;
}

export class Token {
    access_token: string;
    token_type: string;
    refresh_token: string;
    scope: string;
    jti: string;
  }