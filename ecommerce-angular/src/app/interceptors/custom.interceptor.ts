import { HttpInterceptorFn, HttpRequest } from '@angular/common/http';

export const customInterceptor: HttpInterceptorFn = (req, next) => {
  const myToken = sessionStorage.getItem('token');
  
  if (myToken && myToken.length > 0) {
    console.log("tengo token");
    let modifiedReq: HttpRequest<any>;

    // Comprobar si la solicitud es multipart/form-data
    if (req.body instanceof FormData) {
      modifiedReq = req.clone({
        setHeaders: {
          'Accept': '/*',
          'Authorization': `Bearer ${myToken}`
          // No establecemos 'Content-Type' aquí porque el navegador lo hará automáticamente con el boundary correcto para FormData
        }
      });
    } else {
      // Para todas las demás solicitudes (incluyendo JSON)
      modifiedReq = req.clone({
        setHeaders: {
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Authorization': `Bearer ${myToken}`
        }
      });
    }

    return next(modifiedReq);
  } else {
    // Si no hay token, pasar la solicitud sin modificar
    return next(req);
  }
};