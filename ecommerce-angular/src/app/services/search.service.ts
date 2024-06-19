import { EventEmitter, Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
 
  private searchTermSubject: BehaviorSubject<string> = new BehaviorSubject<string>('');

  constructor() {}
  
  setSearchTerm(searchTerm: string): void {
    this.searchTermSubject.next(searchTerm);
  }

  getSearchTerm(): Observable<string> {
    return this.searchTermSubject.asObservable();
  }

  
}