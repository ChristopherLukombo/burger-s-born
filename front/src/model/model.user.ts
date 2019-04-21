export class User {
    constructor(
        public id?: number,
        public pseudo?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public imageUrl?: string,
        public activated?: boolean,
        public birthDay?: Date,
        public roleId?: number,
        public password?: string,
        public createDate?: Date
    ) {}


}

