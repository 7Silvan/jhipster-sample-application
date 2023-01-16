import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectSupportedCosmosDetailComponent } from './project-supported-cosmos-detail.component';

describe('ProjectSupportedCosmos Management Detail Component', () => {
  let comp: ProjectSupportedCosmosDetailComponent;
  let fixture: ComponentFixture<ProjectSupportedCosmosDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectSupportedCosmosDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ projectSupported: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProjectSupportedCosmosDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProjectSupportedCosmosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load projectSupported on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.projectSupported).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
