import { Profile } from "./profile";

export interface Login{
    username : String;
    password : String;
    profile : Profile;
    isOwner : boolean;

}