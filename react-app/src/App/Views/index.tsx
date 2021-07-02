import { Fragment } from 'react';

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

const Views = (props: Props) => {
  const { view } = props;
  switch (view) {
    case 'ANCESTRY':
      return <>Ancestry View</>;
    case 'BACKGROUND':
      return <>Background View</>;
    case 'CLASSES':
      return <>Classes View</>;
    default:
      return <>Default View</>;
  }
};

export default Views;
