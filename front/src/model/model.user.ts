export class User {
    constructor(
        public id?: number,
        public login?: string,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public imageUrl?: string,
        public activated?: boolean,
        public langKey?: string,
        public password?: string
    ) {}


}

