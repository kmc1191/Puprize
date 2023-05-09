import { Appointment } from "./appointment"

export interface Profile{
    name : string
    username : string
    password : string
    email : string
    phone : string
    isOwner : boolean
    //apptID : number
    appointment : Appointment | undefined

}