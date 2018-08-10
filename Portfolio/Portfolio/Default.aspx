<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="Portfolio.Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Ricardo Numa</title>
    <link rel="stylesheet" href="css/estilo.css">
</head>
<body>
    <div class="bg-home">

        <div id="home"></div>

        <span class="hello-world center-div feature">Hello World!</span>

        <span class="name center-div">
            I am
            <br />
            <span class="feature">Ricardo Numa, 
                <br />
                Software Developer.</span>
        </span>

        <span class="home-text center-div">Welcome to my website.</span>

        <a href="#about" class="anchor bottom white">ABOUT ME</a>

    </div>

    <div id="about"></div>

    <div class="bg-about">

        <a href="#home" class="anchor top black">HOME</a>

        <hr class="margin-left-right" style="margin-top: 60px" />

        <h2 class="about">ABOUT ME</h2>

        <p class="about-text margin-left-right">Meu nome é Ricardo Numa, tenho 28 anos, sou formado em Administração de Empresas e depois de trabalhar alguns anos na área percebi que não era feliz fazendo aquilo.</p>
        <p class="about-text margin-left-right">
            Resolvi mudar de área e depois de pesquisar bastante, acabei me identificando com o mundo de TI, mais especificamente com a parte de desenvolvimento. 
	Engraçado lembrar que quando adolecente, passava horas tentando deixar meu site gratuíto (usando praticamente HTML puro) mais legal para mostrar para meus amigos ou 
	editava mapas de jogos para ficarem do jeito que eu gostaria que fossem.
        </p>
        <p class="about-text margin-left-right">
            Em 2012 comecei a fazer um curso de Lógica de Programação e depois passei a estudar por conta própria através de tutoriais da internet, vídeos, artigos, apostilas e livros. 
	Depois de vários meses de estudo, consegui uma oportunidade de trabalhar como programador freelancer numa empresa de TI. 
	Como o foco dela era em infraestrutura e suporte, no começo só conseguia trabalhos pequenos. Mas com o passar do tempo, percebi como isso me ajudou a ganhar confiança e experiência, 
	necessários para conseguir aumentar cada vez mais o nível de complexidade dos meus sistemas. 
	Durante esse período, todas os sistemas que desenvolvi, tanto web quanto desktop, foram em .NET C#, MS SQL Server, MySQL, HTML, CSS e Javascript.
        </p>
        <p class="about-text margin-left-right">
            Em 2013 juntei coragem o suficiente para começar minha segunda graduação, dessa vez em Ciência da Computação. Graças a experiência adquirida no trabalho, conseguia tirar 
	de letra muitas das matérias da faculdade, enquanto que algumas eram totalmente novas para mim. Através dos trabalhos acadêmicos, pude desenvolver diversos sistemas em Java e 
	em 2014 comecei a estudar Android. Em 2015 surgiu a oportunidade de desenvolver um aplicativo Android para a empresa em que trabalhava, e foi aí que surgiu o Arks Capture.
        </p>
        <p class="about-text margin-left-right">
            Atualmente sou responsável pelos serviços relacionados com o sistema de recuperação de crédito de ICMS, no qual participei de todo desenvolvimento. Quando tenho um tempinho, 
	procuro estudar para me especilizar em mobile e web.
        </p>
        <p class="about-text margin-left-right">
            Espero me formar na faculdade no meio de 2017, para que assim possa dedicar mais tempo a minha família e também ao meu hobbie, 
	que por coinscidência, também é o meu trabalho. ; )
        </p>

        <hr class="margin-left-right" />

    </div>

</body>
</html>
