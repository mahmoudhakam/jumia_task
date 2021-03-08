import { PhoneNumbersResponse } from './phonenumber.model';

export interface APIResponse {
  success: boolean;
  errors: {
    message: string;
    type: string;
    timestamp: string;
  };
  code: number;
  payload: PhoneNumbersResponse;
  serviceTime: string;
}
