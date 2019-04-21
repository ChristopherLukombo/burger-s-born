import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import {AuthProviderService} from "./services/auth-provider.service";
import {Observable} from "rxjs";

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(
        private authService: AuthProviderService,
        private router: Router
    ) { }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if (!this.authService.isAuthenticated()) {
            this.router.navigate(['/auth']);
            return false;
        }
        return true;
    }
}
