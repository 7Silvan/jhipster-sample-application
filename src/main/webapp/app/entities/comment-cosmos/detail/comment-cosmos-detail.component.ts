import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommentCosmos } from '../comment-cosmos.model';

@Component({
  selector: 'jhi-comment-cosmos-detail',
  templateUrl: './comment-cosmos-detail.component.html',
})
export class CommentCosmosDetailComponent implements OnInit {
  comment: ICommentCosmos | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comment }) => {
      this.comment = comment;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
