import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CommentCosmosDetailComponent } from './comment-cosmos-detail.component';

describe('CommentCosmos Management Detail Component', () => {
  let comp: CommentCosmosDetailComponent;
  let fixture: ComponentFixture<CommentCosmosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CommentCosmosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ comment: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CommentCosmosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CommentCosmosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load comment on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.comment).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
