import {Component, OnInit} from '@angular/core';
import {User} from "../../model/model.user";
import {ServicesDataService} from "../services/services-data.service";
import {NGXLogger} from 'ngx-logger';
import {environment} from "../../environments/environment";
import {AppConstants} from "../app.constants";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User;

  constructor(
      private servicesDataService: ServicesDataService,
      private logger: NGXLogger
  ) { }

  ngOnInit() {
  }

  save(dataForm): void {
    this.setUser(dataForm);

    if (false === environment.production) {
      this.logger.debug(AppConstants.CALL_SERVICE, this.user);
    }

    this.servicesDataService.save(this.user)
        .subscribe(data => {
          this.logger.info(AppConstants.USER_SAVED_SUCCESSFULLY);
        }, error => {
          this.logger.error(AppConstants.USER_HASNT_BEEN_SAVED, error.message, error.status);
        });

  }

  private setUser(dataForm) {
    this.user = new User();
    this.user.login = dataForm.login;
    this.user.firstName = dataForm.firstName;
    this.user.lastName = dataForm.lastName;
    this.user.email = dataForm.email;
    this.user.password = dataForm.password;
    this.user.imageUrl = dataForm.imageUrl;
    this.user.activated = dataForm.activated;
    this.user.langKey = dataForm.langKey;
  }

}
