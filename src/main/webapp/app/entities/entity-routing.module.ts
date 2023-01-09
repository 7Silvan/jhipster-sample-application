import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cell-type-cosmos',
        data: { pageTitle: 'jhipsterApp.cellType.home.title' },
        loadChildren: () => import('./cell-type-cosmos/cell-type-cosmos.module').then(m => m.CellTypeCosmosModule),
      },
      {
        path: 'project-supported-cosmos',
        data: { pageTitle: 'jhipsterApp.projectSupported.home.title' },
        loadChildren: () => import('./project-supported-cosmos/project-supported-cosmos.module').then(m => m.ProjectSupportedCosmosModule),
      },
      {
        path: 'comment-cosmos',
        data: { pageTitle: 'jhipsterApp.comment.home.title' },
        loadChildren: () => import('./comment-cosmos/comment-cosmos.module').then(m => m.CommentCosmosModule),
      },
      {
        path: 'cell-model-cosmos',
        data: { pageTitle: 'jhipsterApp.cellModel.home.title' },
        loadChildren: () => import('./cell-model-cosmos/cell-model-cosmos.module').then(m => m.CellModelCosmosModule),
      },
      {
        path: 'publication-cosmos',
        data: { pageTitle: 'jhipsterApp.publication.home.title' },
        loadChildren: () => import('./publication-cosmos/publication-cosmos.module').then(m => m.PublicationCosmosModule),
      },
      {
        path: 'characterization-data-cosmos',
        data: { pageTitle: 'jhipsterApp.characterizationData.home.title' },
        loadChildren: () =>
          import('./characterization-data-cosmos/characterization-data-cosmos.module').then(m => m.CharacterizationDataCosmosModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
