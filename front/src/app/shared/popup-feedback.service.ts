import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { PopupFeedback } from '../core/models/popup-feedback.model';

@Injectable({
  providedIn: 'root'
})
export class PopupFeedbackService {

  private readonly _feed$: BehaviorSubject<PopupFeedback> = new BehaviorSubject(new PopupFeedback("","info"));

  getFeed(): Observable<PopupFeedback> {
    return this._feed$.asObservable();
  }

  setFeed(pfb: PopupFeedback): void {
    this._feed$.next(pfb);
    setTimeout(()=>{
      this.removeFeed();
    }, 2000);
  }

  removeFeed(): void {
    this._feed$.next(new PopupFeedback("", "info"));
  }
}
