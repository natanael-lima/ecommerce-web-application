export class Product {
    id!: number;
    name: string;
    description: string;
    price: number; // Tipo corregido de double a number
    stock: number;
    image: string;
    timestamp: Date; // Tipo corregido de date a Date
    highlight: Boolean;
    categoria: string;
  
    constructor(
      name: string, 
      description: string, 
      price: number, 
      stock: number, 
      image: string, 
      timestamp: Date,
      highlight: Boolean, 
      categoria: string
    ) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.stock = stock;
      this.image = image;
      this.timestamp = timestamp;
      this.highlight=highlight;
      this.categoria = categoria;
    }
  }
  