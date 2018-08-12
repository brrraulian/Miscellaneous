<%@ Page Title="" Language="C#" MasterPageFile="~/Portfolio.Master" AutoEventWireup="true" CodeBehind="Portifolio.aspx.cs" Inherits="Portfolio.Portifolio" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    
    <div id="about" class="page margin-left-right">
               
        <div class="bg-white">

            <h2 class="about">ABOUT ME</h2>

            <p class="about-text">Meu nome é Ricardo Numa, tenho 28 anos, sou formado em Administração de Empresas e depois de trabalhar alguns anos na área percebi que não era feliz fazendo aquilo.</p>
            <p class="about-text">
                Resolvi mudar de área e depois de pesquisar bastante, acabei me identificando com o mundo de TI, mais especificamente com a parte de desenvolvimento. 
	Engraçado lembrar que quando adolecente, passava horas tentando deixar meu site gratuíto (usando praticamente HTML puro) mais legal para mostrar para meus amigos ou 
	editava mapas de jogos para ficarem do jeito que eu gostaria que fossem.
            </p>
            <p class="about-text">
                Em 2012 comecei a fazer um curso de Lógica de Programação e depois passei a estudar por conta própria através de tutoriais da internet, vídeos, artigos, apostilas e livros. 
	Depois de vários meses de estudo, consegui uma oportunidade de trabalhar como programador freelancer numa empresa de TI. 
	Como o foco dela era em infraestrutura e suporte, no começo só conseguia trabalhos pequenos. Mas com o passar do tempo, percebi como isso me ajudou a ganhar confiança e experiência, 
	necessários para conseguir aumentar cada vez mais o nível de complexidade dos meus sistemas. 
	Durante esse período, todas os sistemas que desenvolvi, tanto web quanto desktop, foram em .NET C#, MS SQL Server, MySQL, HTML, CSS e Javascript.
            </p>
            <p class="about-text">
                Em 2013 juntei coragem o suficiente para começar minha segunda graduação, dessa vez em Ciência da Computação. Graças a experiência adquirida no trabalho, conseguia tirar 
	de letra muitas das matérias da faculdade, enquanto que algumas eram totalmente novas para mim. Através dos trabalhos acadêmicos, pude desenvolver diversos sistemas em Java e 
	em 2014 comecei a estudar Android. Em 2015 surgiu a oportunidade de desenvolver um aplicativo Android para a empresa em que trabalhava, e foi aí que surgiu o Arks Capture.
            </p>
            <p class="about-text">
                Atualmente sou responsável pelos serviços relacionados com o sistema de recuperação de crédito de ICMS, no qual participei de todo desenvolvimento. Quando tenho um tempinho, 
	procuro estudar para me especilizar em mobile e web.
            </p>
            <p class="about-text">
                Espero me formar na faculdade no meio de 2017, para que assim possa dedicar mais tempo a minha família e também ao meu hobbie, 
	que por coinscidência, também é o meu trabalho. ; )
            </p>

        </div>



        <div id="projects"></div>

        <div class="bg-white">

            <hr style="margin-top: 80px" />

            <h2 class="about">PROJECTS</h2>

            <div align="center">

                <table>
                    <tr>
                        <td class="project-cell project-cell-left"><a href="Arks.aspx" target="#">
                            <img class="project-image" src="img/projects/arks.png" alt="Arks Capture"></a></td>
                        <td class="project-cell project-cell-center"><a href="Icms.aspx" target="#">
                            <img class="project-image" src="img/icms.jpg" alt="ICMS"></a></td>
                        <td class="project-cell project-cell-right"><a href="Ceb.aspx" target="#">
                            <img class="project-image" src="img/projects/ceb.png" alt="Cenoura & Bronze"></a></td>
                    </tr>
                    <tr>
                        <td class="project-cell project-cell-left"><a href="https://play.google.com/store/apps/details?id=br.com.izio.condor" target="#">
                            <img class="project-image" src="img/projects/condor.png" alt="Clube Condor"></a></td>
                        <td class="project-cell project-cell-center"><a href="https://play.google.com/store/apps/details?id=com.stoneridge.eld" target="#">
                            <img class="project-image" src="img/projects/eld.png" alt="Stoneridge EZ-ELD"></a></td>
                        <td class="project-cell project-cell-right"><a href="Izio.aspx" target="#">
                            <img class="project-image" src="img/projects/izio.png" alt="Izio"></a></td>
                    </tr>
                    <tr>
                        <td class="project-cell project-cell-left"><a href="Pernambucanas.aspx" target="#">
                            <img class="project-image" src="img/projects/pernambucanas.png" alt="Auto Atendimento Pernambucanas"></a></td>
                        <td class="project-cell project-cell-center"><a href="https://play.google.com/store/apps/details?id=br.com.dahora.petcare" target="#">
                            <img class="project-image" src="img/projects/petcare.png" alt="Bulário Pet Care"></a></td>
                        <td class="project-cell project-cell-right"><a href="https://play.google.com/store/apps/details?id=br.com.hypermarcas.risque" target="#">
                            <img class="project-image" src="img/projects/risque.png" alt="Risqué"></a></td>
                    </tr>
                    <tr>
                        <td class="project-cell project-cell-left"><a href="https://play.google.com/store/apps/details?id=br.com.shyre" target="#">
                            <img class="project-image" src="img/projects/shyre.png" alt="Shyre"></a></td>
                        <td class="project-cell project-cell-center"><a href="https://play.google.com/store/apps/details?id=com.trrsecuritas.segurados" target="#">
                            <img class="project-image" src="img/projects/trr.png" alt="TRR"></a></td>
                    </tr>
                </table>

            </div>

            <hr />

        </div>



        <div id="contact"></div>

        <div class="bg-white">

            <h2 class="about">CONTACT</h2>

            <p class="about-text">Meu nome é Ricardo Numa, tenho 28 anos, sou formado em Administração de Empresas e depois de trabalhar alguns anos na área percebi que não era feliz fazendo aquilo.</p>

            <hr />

        </div>

    </div>

</asp:Content>
