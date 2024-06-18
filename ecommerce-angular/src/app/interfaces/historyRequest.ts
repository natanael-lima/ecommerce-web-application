export interface HistoryRequest {
    id:number;
    action:string;
    timestamp:Date;
    tableType: Date; // Tipo corregido de date a Date
}
