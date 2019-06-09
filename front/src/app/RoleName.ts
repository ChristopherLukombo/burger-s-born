import {Role} from '../model/model.role';

export class RoleName {
    public static readonly ROLE_CUSTOMER = 'ROLE_CUSTOMER';
    public static readonly ROLE_ADMIN = 'ROLE_ADMIN';

    public static readonly ROLES = [
        new Role(1, RoleName.ROLE_CUSTOMER),
        new Role(2, RoleName.ROLE_ADMIN)
    ];

    private constructor() {
        // Empty constructor to prevent to create Object
    }
}
