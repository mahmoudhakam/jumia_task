import {
  AfterViewInit,
  Component,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { APIResponse } from 'src/app/shared/models/api-response.model';
import {
  CountryDTO,
  CustomerDTO,
  Filter,
  PhoneNumbersResponse,
} from 'src/app/shared/models/phonenumber.model';
import { PhonenumberListingService } from '../../services/domain/phonenumber-listing.service';

@Component({
  selector: 'app-phone-number-page',
  templateUrl: './phone-number-page.component.html',
  styleUrls: ['./phone-number-page.component.css'],
})
export class PhoneNumberPageComponent
  implements AfterViewInit, OnInit, OnDestroy {
  displayedColumns: string[] = [
    'customername',
    'phone',
    'state',
    'countryname',
    'countrycode',
  ];

  dataSource = new MatTableDataSource<CustomerDTO>();

  @ViewChild(MatPaginator) paginator: MatPaginator;
  subscriptions: Subscription[] = [];
  requestParameters: RequestParameters = {
    offSet: '0',
    pageSize: '10',
    state: '',
    countries: [],
  };

  phoneNumberResponse: PhoneNumbersResponse | undefined;
  errorMessage: string | undefined;
  rows: CustomerDTO[] | undefined;
  countries: CountryDTO[] = [];
  states: number[] = [];

  constructor(private phoneService: PhonenumberListingService) { }

  ngOnInit(): void {
    this.subscriptions.push(this.listPhoneNumbers());
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  listPhoneNumbers(): Subscription {
    return this.phoneService
      .listAllPhoneNumbers(this.requestParameters)
      .subscribe(
        (response: APIResponse) => {
          if (response.payload != null) {
            this.phoneNumberResponse = response.payload;
            this.dataSource = new MatTableDataSource<CustomerDTO>(
              this.phoneNumberResponse.customers
            );
            console.log('countries',this.countries);
            this.countries = this.phoneNumberResponse?.filter?.countries
            this.states = this.phoneNumberResponse?.filter?.states
          }
          if (response.errors != null) {
            this.dataSource = new MatTableDataSource<CustomerDTO>(
             []
            );
            this.errorMessage = response.errors.message;
          }
        },
        (error) => {
          console.log('error', error);
        }
      );
  }

  paging(offSet: any): void {
    this.requestParameters.offSet = offSet;
    this.subscriptions.push(this.listPhoneNumbers());
  }

  onResetFilters(): void {
    this.requestParameters.countries = [];
    this.requestParameters.state = '';
    this.subscriptions.push(this.listPhoneNumbers());
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((element) => {
      element.unsubscribe();
    });
  }
}

export interface RequestParameters {
  offSet: string;
  pageSize: string;
  state: string;
  countries: string[];
}
