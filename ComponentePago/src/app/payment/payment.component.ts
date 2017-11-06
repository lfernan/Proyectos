import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, FormBuilder, Validators } from '@angular/forms';
import { ServicioService } from "app/servicio.service";
import { User } from './User';
declare var $: any;

@Component({
  selector: 'nev-payment',
  templateUrl: 'payment.component.html',
  styleUrls: ['payment.component.css']
})

export class PaymentComponent implements OnInit {
  form: FormGroup;
  submitted: boolean;
  token: string;
  json: any;
  monto: number;
  valido: boolean;
  busy: boolean;
  left: number;
  plan: number;
  planes: any;
  seleccion: any;
  user: User;
  fin: boolean;
  codOperacion: number;
  dniMask = [/[0-9]/, /\d/, '.', /\d/, /\d/, /\d/, '.', /\d/, /\d/, /\d/];
  expiracionMask = [/[0-9]/, /\d/, '/', /\d/, /\d/];
  cvcMask = [/[0-9]/, /\d/, /\d/];
  ocrMask = [/[0-9]/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/];


  constructor(private route: ActivatedRoute, private servicio: ServicioService, private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.user = new User();
    let params: any = this.route.snapshot.params;
    this.monto = Number(params.monto.replace(/,/g, "."));
    this.token = params.token;
    this.codOperacion = params.codOperacion;
    this.busy = false;
    this.valido = false;
    this.plan = 0;
    this.form = this.fb.group({
      nombre: ['', [<any>Validators.required]],
      ocr: [{ value: '', disabled: false }, [<any>Validators.required]],
      cvc: [{ value: '', disabled: false }, [<any>Validators.required]],
      expiracion: [{ value: '', disabled: false }, [<any>Validators.required]],
      dni: [{ value: '', disabled: false }, [<any>Validators.required]]
    });
  }

  confirmar(model: any, isValid: boolean) {
    let cvc = model.cvc;
    let expiracion = Number('20' + model.expiracion.substr(3, 5) + model.expiracion.substr(0, 2));
    let ocr = Number(model.ocr.replace(/-/g, "").replace(/_/g, ""));
    let nombre = model.nombre;
    this.busy = true;
    this.servicio.autorizarCompra(this.monto, ocr, cvc, expiracion, this.token, this.plan, this.codOperacion).subscribe(res => {
      this.json = res;
      this.submitted = true;
      if (this.json.codigoError === 0) {
        this.busy = false;
        this.json.observacion = 'Pago registrado correctamente Nro. Operación ' + this.json.codigoAutorizacion;
        this.showOk();
        this.fin = true;
      } else if (this.json.codigoError != 0) {
        this.busy = false;
        this.showErr();
      }
      this.plan = 0;
      this.valido = false;
    });
  }

  onKeyNombre(event: any) {
    this.user.nombre = event.target.value;
    this.validar();
  }

  onKeyOcr(event: any) {
    this.user.ocr = event.target.value.replace(/-/g, "").replace(/_/g, "");
    this.validar();
  }

  onKeyExpiracion(event: any) {
    this.user.expiracion = event.target.value.replace(/\//g, "").replace(/_/g, "");
    if (this.user.expiracion.toString().length == 4) {
      this.user.expiracion = Number('20' + this.user.expiracion.toString().substr(2, 5) + this.user.expiracion.toString().substr(0, 2));
    }
    this.validar();
  }

  onKeyCvc(event: any) {
    if (!isNaN(parseInt(event.target.value))) {
      this.user.cvc = event.target.value;
    }
    this.validar();
  }

  onKeyDni(event: any) {
    this.user.dni = event.target.value.replace(/\./g, "").replace(/_/g, "");
    this.validar();
  }

  onClickPlan(plan: any, event: any) {
    if (this.seleccion != null) {
      $('#' + this.seleccion).removeClass('td-selection')
    }
    this.plan = plan;
    this.seleccion = event.target.parentElement.id;
    event.target.parentElement.className = 'td-selection';
    $('.modal').modal('close');
  }

  showPlanes() {
    this.servicio.planes(this.user.dni, 600000017, this.monto).subscribe(res => {
      this.planes = res;
      $('.modal').modal('open', { dismissible: false });
    });
  }

  closePlanes() {
    $('.modal').modal('close');
    this.plan = 0;
  }

  private validar() {
    if (this.submitted != null) {
      this.submitted = false;
    }
    if (this.user.nombre.toString().length > 0 && this.user.ocr.toString().length == 16
      && this.user.expiracion.toString().length == 6 && this.user.dni.toString().length == 8
      && this.user.cvc.toString().length == 3) {
      this.busy = true;
      this.servicio.validarDatos(this.user, this.token).subscribe(res => {
        let validacion = res;
        if (validacion.codigoError === 0) {
          this.valido = true;
          this.submitted = false;
        } else if (validacion.codigoError != 0) {
          this.submitted = true;
          this.valido = false;
          if (validacion.codigoError === 2) {
            this.showErr();
            this.fin = true;
          }
        }
        this.busy = false;
        this.json = validacion;
      });
    } else {
      this.valido = false;
      this.busy = false;
    }
  }

  private showOk() {
    $('.input-field').fadeOut('slow', function () {
      $('.ok').show('slow', function () {
        $(this).animate({
          width: "100%",
          height: "100%",
          fontSize: "2em"
        }, 500);
      });
    });
  }

  private showErr() {
    $('.input-field').fadeOut('slow', function () {
      $('.err').show('slow', function () {
        $(this).animate({
          width: "100%",
          height: "100%",
          fontSize: "2em"
        }, 500);
      });
    });
  }
}