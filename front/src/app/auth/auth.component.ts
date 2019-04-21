import {Component, OnInit} from '@angular/core';
import {NGXLogger} from "ngx-logger";
import {TranslateService} from "@ngx-translate/core";
import {Login} from "../../model/model.login";
import {AuthProviderService} from "../services/auth-provider.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpErrorResponse} from "@angular/common/http";
@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css']
})
export class AuthComponent implements OnInit {

  login: Login;
  registerForm: FormGroup;
  submitted = false;
  errorMessage: String;

  constructor(
      private authProviderService: AuthProviderService,
      private logger: NGXLogger,
      private translateService: TranslateService,
      private router: Router,
      private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.createForm();
  }

  private createForm() {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.maxLength(50)]],
      password: ['', [Validators.required, Validators.minLength(12), Validators.maxLength(20)]]
    });
  }

// convenience getter for easy access to form fields
  get f() { return this.registerForm.controls; }

  authenticate() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }

    this.setLogin();

    this.authProviderService.authenticate(this.login)
        .subscribe(data => {
          this.authenticateSuccess(data);
          this.router.navigate(['home']);
        }, err => {
          if (err instanceof HttpErrorResponse) {
            if (403 === err.status) {
              this.errorMessage = 'Login ou mot de passe incorrecte';
            } else {
              this.errorMessage = 'Une erreur serveur s\'est produite';
            }
          }
        });

  }

  private setLogin() {
    this.login = new Login();
    this.login.username = this.registerForm.get('username').value;
    this.login.password = this.registerForm.get('password').value;
  }

  private authenticateSuccess(resp) {
    const bearerToken = resp.headers.get('Authorization');

    if (bearerToken && 'Bearer ' === bearerToken.slice(0, 7)) {
      const jwt = bearerToken.slice(7, bearerToken.length);
      this.authProviderService.storeAuthenticationToken(jwt);
      return jwt;
    }
  }


}
