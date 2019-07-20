import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { AuthProviderService } from './services/auth-provider.service';
import { Observable } from 'rxjs';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(
        private authService: AuthProviderService,
        private router: Router
    ) { }

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        if (this.authService.isAuthenticated()) {
            // check if route is restricted by role
            if (route.data.roles && route.data.roles.indexOf(this.authService.getRole()) === -1) {
                // role not authorised so redirect to home page
                this.router.navigate(['/home']);
                return false;
            }
            return true;
        }
        // not logged in so redirect to login page with the return url
        this.router.navigate(['/auth']);
        return false;
    }
}
