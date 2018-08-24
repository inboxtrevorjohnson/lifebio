import {Common} from '../Common';

export class ContactDetails extends Common {
  contactAddresses: ContactAddress[];
  contactEmailAddresses: ContactEmailAddress[];
  contactNumbers: ContactNumber[];
}

export class ContactMeans extends Common {
  contactType: ContactType;
}

export class ContactAddress extends ContactMeans {
  line1: string;
  line2: string;
  line3: string;
  cityArea: string;
  state: string;
  postalCode: string;
  country: string;
}

export class ContactEmailAddress extends ContactMeans {
  emailAddress: string;
}

export class ContactNumber extends ContactMeans {
  number: string;
}

export enum ContactType {
  EMPTY = '',
  PERSONAL = 'Personal',
  BUSINESS = 'Business',
  RESIDENTIAL = 'Residential',
}

