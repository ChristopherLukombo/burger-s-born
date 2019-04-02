import {Component, OnInit} from '@angular/core';
import {User} from "../../model/model.user";
import {ServicesDataService} from "../services/services-data.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user: User;

  constructor(private servicesDataService: ServicesDataService) { }

  ngOnInit() {
  }

  save(dataForm): void {
    this.user = new User();
    this.user.login = dataForm.login;
    this.user.firstName = dataForm.firstName;
    this.user.lastName = dataForm.lastName;
    this.user.email = dataForm.email;
    this.user.password = dataForm.password;
    this.user.imageUrl = dataForm.imageUrl;
    this.user.activated = dataForm.activated;
    this.user.langKey = dataForm.langKey;

    console.log(this.user.firstName);
      this.servicesDataService.save(this.user).subscribe(data => {
        console.log('success');

      }, error => {
        // TODO utiliser un LOGGER
        console.error('error', error);
      });

  }
}
