export interface PhoneNumbersResponse {
  totalItems: number;
  totalPages: number;
  customers: CustomerDTO[];
  filter: Filter;
}

export interface CustomerDTO {
  id: number;
  phone:string;
  name: string;
  state: PhoneNumberState;
  country: CountryDTO;
}

export interface CountryDTO {
  id: number;
  name: string;
  code: string;
}

export interface Filter {
  states: PhoneNumberState[];
  countries: CountryDTO[];
}

export enum PhoneNumberState {
  VALID,
  NOT_VALID,
}
