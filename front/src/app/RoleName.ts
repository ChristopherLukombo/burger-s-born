import {Role} from "../model/model.role";

export class RoleName {
    public static readonly ROLE_CUSTOMER = 'ROLE_CUSTOMER';


    public static ROLES = [
        new Role(1, "ROLE_CUSTOMER")
    ];

    private constructor() {
        // Empty constructor to prevent to create Object
    }
}
