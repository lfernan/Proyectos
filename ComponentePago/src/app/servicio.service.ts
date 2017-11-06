import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import { User } from "app/payment/User";

@Injectable()
export class ServicioService {

  constructor(private _http: Http) { }

  autorizarCompra(monto: number, ocr: number, cvc: number, expiracion: number, token: string, plan: number, codOperacion: number) {
    let header = new Headers();
    header.append('Content-Type', 'application/x-www-form-urlencoded');
    let options = new RequestOptions({ headers: header });
    let parameters = new URLSearchParams();
    parameters.set('importe', monto.toString());
    parameters.set('tarjeta', ocr.toString());
    parameters.set('cvc', cvc.toString());
    parameters.set('comercio', '600000017');
    parameters.set('plan', plan.toString());
    parameters.set('moneda', '0');
    parameters.set('token', token);
    parameters.set('terminal', 'gateway');
    parameters.set('codOperacion', codOperacion.toString());
    return this._http.post('http://127.0.0.1:7101/wls12-campanias/resources/api/autorizarCompra', parameters.toString(), options)
    //return this._http.post('http://appsoasdesa.tarjetanevada.com.ar/wls12-campanias/resources/api/autorizarCompra', parameters.toString(), options)
      .map(res => res.json());
  }

  validarDatos(user: User, token: string) {
    let header = new Headers();
    header.append('Content-Type', 'application/x-www-form-urlencoded');
    let options = new RequestOptions({ headers: header });
    let parameters = new URLSearchParams();
    parameters.set('nombre', user.nombre);
    parameters.set('ocr', user.ocr.toString());
    parameters.set('dni', user.dni.toString());
    parameters.set('cvc', user.cvc.toString());
    parameters.set('token', token);
    parameters.set('expiracion', user.expiracion.toString());
    return this._http.post('http://127.0.0.1:7101/wls12-campanias/resources/api/validar', parameters.toString(), options)
    //return this._http.post('http://appsoasdesa.tarjetanevada.com.ar/wls12-campanias/resources/api/validar', parameters.toString(), options)
      .map(res => res.json());
  }

  login(entidad: string, serial: string) {
    let header = new Headers();
    header.append('Content-Type', 'application/x-www-form-urlencoded');
    let options = new RequestOptions({ headers: header });
    let parameters = new URLSearchParams();
    parameters.set('entidad', entidad);
    parameters.set('serial', serial);
    return this._http.post('http://127.0.0.1:7101/wls12-campanias/resources/api/login',parameters.toString(), options)
    //return this._http.post('http://appsoasdesa.tarjetanevada.com.ar/wls12-campanias/resources/api/login', parameters.toString(), options)
      .map(res => res.json());
  }

  planes(documento: number, comercio: number, deuda: number) {
    let header = new Headers();
    header.append('Content-Type', 'text/plain');
    let options = new RequestOptions({ headers: header });
    return this._http.get('http://127.0.0.1:7101/wls12-campanias/resources/api/planes/' + documento + '/' + comercio + '/' + deuda, options)
    //return this._http.get('http://appsoasdesa.tarjetanevada.com.ar/wls12-campanias/resources/api/planes/' + documento + '/' + comercio + '/' + deuda, options)
      .map(res => res.json());
  }

  test() {
    let header = new Headers();
    header.append('Content-Type', 'text/plain');
    let options = new RequestOptions({ headers: header });
    //return this._http.get('http://127.0.0.1:7101/wls12-campanias/resources/api/test', options).map(res => res.text());
    return this._http.get('http://appsoasdesa.tarjetanevada.com.ar/wls12-campanias/resources/api/test', options)
      .map(res => res.text());
  }
}
