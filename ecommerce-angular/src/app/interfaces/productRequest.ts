export interface ProductRequest {
    id: number;
    name: string;
    description: string;
    price: number; // Tipo corregido de double a number
    stock: number;
    image: string;
    timestamp: Date; // Tipo corregido de date a Date
    highlight: Boolean;
    categoria: string;
   
  
}
