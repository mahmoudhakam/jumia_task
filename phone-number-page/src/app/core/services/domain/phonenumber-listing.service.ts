import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { PhoneNumbersResponse } from 'src/app/shared/models/phonenumber.model';
import { HttpService } from '../http/http.service';
import { Observable } from 'rxjs';
import { RequestParameters } from '../../components/phone-number-page/phone-number-page.component';

@Injectable({
  providedIn: 'root',
})
@Injectable()
export class PhonenumberListingService {
  serviceName: string = 'phone-numbers';

  constructor(private http: HttpService<any>) {}

  listAllPhoneNumbers(requestParameters: RequestParameters): Observable<any> {
    console.log('start calling phone service');
    return this.http.get(
      this.serviceName,
      this.getParameters(
        requestParameters.offSet,
        requestParameters.pageSize,
        requestParameters.state,
        requestParameters.countries
      )
    );
  }

  getParameters(
    offSet: string,
    pageSize: string,
    state: string,
    countries: string[]
  ): HttpParams {
    let params = new HttpParams();
    params = params.append('offSet', offSet == null ? '0' : offSet);
    params = params.append('pageSize', pageSize == null ? '10' : pageSize);
    if (state != '') {
      params = params.append('state', state);
    }
    if (countries.length > 0) {
      countries.forEach((c) => {
        params = params.append('countries', c);
      });
    }
    return params;
  }
}
