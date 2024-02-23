import { Component } from '@angular/core';
import { PopupFeedbackService } from 'src/app/shared/popup-feedback.service';
import { PopupFeedback } from '../../models/popup-feedback.model';

@Component({
  selector: 'app-popup-feedback',
  templateUrl: './popup-feedback.component.html',
  styleUrls: ['./popup-feedback.component.scss']
})
export class PopupFeedbackComponent {

  currentFeedBack!: PopupFeedback;

  constructor(
    private pfs: PopupFeedbackService
  ){}

  ngOnInit(): void {
    this.pfs.getFeed().subscribe(pfb => this.currentFeedBack = pfb);
  }

}
