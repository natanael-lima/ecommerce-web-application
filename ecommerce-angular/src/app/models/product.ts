export class Product {
    id!: number;
    name: string;
    description: string;
    price: number; // Tipo corregido de double a number
    stock: number;
    image: string;
    timestamp: Date; // Tipo corregido de date a Date
    highlights: Boolean;
    categoria: string;
  
    constructor(
      name: string, 
      description: string, 
      price: number, 
      stock: number, 
      image: string, 
      timestamp: Date,
      highlights: Boolean, 
      categoria: string
    ) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.stock = stock;
      this.image = image;
      this.timestamp = timestamp;
      this.highlights=highlights;
      this.categoria = categoria;
    }
  }
  