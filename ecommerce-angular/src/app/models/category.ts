export class Category {
  name: string;
  description: string;
  timestamp: Date;

  constructor(name: string, description: string, timestamp: Date) {
      this.name = name;
      this.description = description;
      this.timestamp = timestamp;
  }
}
