import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { PaymentComponent } from './payment/payment.component';
import { TextMaskModule } from 'angular2-text-mask';
import { ServicioService } from "app/servicio.service";
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MdButtonModule, MdCheckboxModule,MdCardModule,MdInputModule } from '@angular/material';
import 'hammerjs';

const appRoutes: Routes = [
  //{ path: '', redirectTo: 'test', pathMatch: 'full' },
  { path: 'pago/:monto/:token/:codOperacion', component: PaymentComponent },
];

@NgModule({
  declarations: [
    AppComponent,
    PaymentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    TextMaskModule,
    HttpModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NoopAnimationsModule,
    MdButtonModule,
    MdCheckboxModule,
    MdCardModule,
    MdInputModule,
    RouterModule.forRoot(
      appRoutes,
      {
        enableTracing: false,
        useHash:true
      }
    )
  ],
  providers: [ServicioService],
  bootstrap: [AppComponent]
})
export class AppModule { }
