import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthProviderService } from '../services/auth-provider.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isAdmin: boolean;

  constructor(
    public authProviderService: AuthProviderService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.authProviderService.admin.subscribe(value => {
      this.isAdmin = value;
    });
    this.isAdmin = this.isAdmin || this.authProviderService.isAdmin();
  }

  public logout() {
    this.authProviderService.logout()
      .subscribe();
    this.router.navigate(['/auth']);
  }
}


