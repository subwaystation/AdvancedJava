# AdvancedJava

# Rna3DViewer
Entry point: StartRna3DViewer
All classes needed for this program can be found in package "_rna_3d_viewer".

# Functionality
The program is able to read in a PDB file containing RNA residues. From these
atoms of the read in residues the programs then generates a 3D-Structure.
SHIFT + LEFT_CLICK + MOUSE: ZOOM
LEFT_CLICK + MOUSE: SPIN
Furthermore, the hydrogen bonds can be made visible under "VIEW". This menu has also 
further options like "Base Coloring" or "Type Coloring".
By clicking on SecStruct -> "Open SecStruct" another window pops up where the primary 
and secondary structure can be drawn. Moreover, the calculated pseudoknots can be drawn,
too.
Every nucleotide in every structure type is selectable, making it yellow in all structures.
If a nucleotide pairing line in the secondary structure was clicked, then the 
corresponding nucleotides are highlighted in the primary and tertiary structure, respectively.

# drawing package
secondary structure classes provided by Prof. Huson

# io package
pdb record and parsing classes

# model package
## coordinate_extractor package
contains classes for the extraction of relevant and special coordinates for drawing out of a
residue
## structure
here classes are stored that represent a structure, overall abstract structure is "ANucleotideStructure"
except for "SecondaryStructure"
## structure_builder
provides classes to build 3D structures

# rna_2d_drawer package
classes for the secondary and primary structure representations view

# view
classes for the 3D viewer view

```JADE
doctype html
html(lang="en")
  head
    title= pageTitle
    script(type='text/javascript').
      if (foo) bar(1 + 5)
  body
    h1 Pug - node template engine
    #container.col
      if youAreUsingPug
        p You are amazing
      else
        p Get on it!
      p.
        Pug is a terse and simple templating language with a
        strong focus on performance and powerful features.
```
becomes
```HTML
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>Pug</title>
    <script type="text/javascript">
      if (foo) bar(1 + 5)
    </script>
  </head>
  <body>
    <h1>Pug - node template engine</h1>
    <div id="container" class="col">
      <p>You are amazing</p>
      <p>Pug is a terse and simple templating language with a strong focus on performance
      and powerful features.</p>
    </div>
  </body>
</html>
```
