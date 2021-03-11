import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CookieService } from "ngx-cookie";
import { Observable } from "rxjs";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor(private cookieService: CookieService){}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token = this.cookieService.get("token");
        let sessions = this.cookieService.get("JSESSIONID");
        if (token) {
            request = request.clone({
                setHeaders: {
                    Authorization: 'Bearer ' + token
                }
            });
        } else if(sessions){
            request = request.clone({
                withCredentials: true,
                setHeaders: {
                    Cookie: sessions
                }
            });
        }
        return next.handle(request);
    }
}