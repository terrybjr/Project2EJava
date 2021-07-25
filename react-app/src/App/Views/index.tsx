import { Grid } from '@material-ui/core';
import { Component, Fragment } from 'react';
import Abilities from '../Abilities';
import Ancestry from '../Ancestry';

export type DefaultView = '';
export type AncestryView = 'ANCESTRY';
export type BackgroundView = 'BACKGROUND';
export type ClassesView = 'CLASSES';
export type AbilitiesView = 'ABILITIES';
export type FeatsView = 'FEATS';
export type ViewTypes =
  | DefaultView
  | AncestryView
  | BackgroundView
  | ClassesView
  | AbilitiesView
  | FeatsView;

type Props = {
  view: ViewTypes;
};

class Views extends Component<Props> {
  renderStep() {
    switch (this.props.view) {
      case 'ANCESTRY':
        return <Ancestry />;
      case 'BACKGROUND':
        return <>Background View</>;
      case 'CLASSES':
        return <>Classes View</>;
      default:
        return <>Default View</>;
    }
  }

  render() {
    return (
      <Grid container spacing={2}>
        <Grid item xs={12} sm={9}>
          {this.renderStep()}
        </Grid>
        <Grid item xs={12} sm={3}>
          {this.props.view === 'ANCESTRY' ?
          <Abilities /> : <div/>}
        </Grid>
      </Grid>
    );
  }
}

export default Views;
